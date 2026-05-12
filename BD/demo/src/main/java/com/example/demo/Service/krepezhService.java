package com.example.demo.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.gost;
import com.example.demo.Repository.krepezhRepository;

@Service
public class krepezhService {

    @Autowired
    private krepezhRepository repository;

    public List<Map<String, Object>> getAll() {
        return repository.getAll();
    }

    public Map<String, Object> getOne(Integer id) {
        return repository.getOne(id);
    }

    public List<gost> search(Map<String, String> params) {
        return repository.search(params);
    }

    public void add(gost gost) throws Exception {
        repository.add(gost);
    }

    public void update(Integer id, gost gost) throws Exception{
        repository.update(id, gost);
    }

    public void delete(Integer id) {
        repository.delete(id);
    }
}