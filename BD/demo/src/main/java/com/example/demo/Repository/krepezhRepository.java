package com.example.demo.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.Model.gost;

import tools.jackson.databind.ObjectMapper;



@Repository
public class krepezhRepository {

    @Autowired
    private JdbcTemplate jdbc;
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> rows = jdbc.queryForList("SELECT id, title, type, gost_number, properties::text FROM gost");
        
        for (Map<String, Object> row : rows) {
            String propsJson = (String) row.get("properties");
                row.put("properties", mapper.readValue(propsJson, List.class));
        }
        return rows;
    }
    


    public Map<String, Object> getOne(Integer id) {
        Map<String, Object> row = jdbc.queryForMap("SELECT id, title, type, gost_number, properties::text FROM gost WHERE id = ?", id);
    
        String propsJson = (String) row.get("properties");
        row.put("properties", mapper.readValue(propsJson, List.class));
    
        return row;
    }

    public List<gost> search(Map<String, String> params) {
        StringBuilder sql = new StringBuilder("SELECT id, title, type, gost_number, properties::text FROM gost WHERE 1=1");
        
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            
            if (key.equals("type") || key.equals("title") || key.equals("gost_number")) {
                sql.append(" AND ").append(key).append(" = '").append(val).append("'");
            } else {
                if (val.matches("-?\\d+(\\.\\d+)?")) {
                    sql.append(" AND EXISTS (SELECT 1 FROM jsonb_array_elements(properties) AS elem WHERE elem->>'key' = '").append(key).append("' AND (elem->>'value')::numeric = ").append(val).append(")");
                } else {
                    sql.append(" AND EXISTS (SELECT 1 FROM jsonb_array_elements(properties) AS elem WHERE elem->>'key' = '").append(key).append("' AND elem->>'value' = '").append(val).append("')");
                }
            }
        }
        
        List<Map<String, Object>> rows = jdbc.queryForList(sql.toString());
        List<gost> result = new ArrayList<>();
        
        for (Map<String, Object> row : rows) {
            gost g = new gost();
            g.setId((Integer) row.get("id"));
            g.setTitle((String) row.get("title"));
            g.setType((String) row.get("type"));
            g.setGostNumber((String) row.get("gost_number"));
            
            String propsJson = (String) row.get("properties");
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> props = mapper.readValue(propsJson, List.class);
            g.setProperties(props);
            
            result.add(g);
        }
        return result;
    }

    public void add(gost g) throws Exception {
        jdbc.update("INSERT INTO gost (name, type, gost_number, description, properties) VALUES (?,?,?,?,?::jsonb)",
            g.getTitle(), g.getType(), g.getGostNumber(), mapper.writeValueAsString(g.getProperties()));
}

    public void update(Integer id, gost g) throws Exception {
        jdbc.update("UPDATE gost SET name=?, type=?, gost_number=?, description=?, properties=?::jsonb WHERE id=?",
            g.getTitle(), g.getType(), g.getGostNumber(), mapper.writeValueAsString(g.getProperties()), id);
}

    public void delete(Integer id) {
        jdbc.update("DELETE FROM gost WHERE id = ?", id);
    }
}

