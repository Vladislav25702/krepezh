package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Model.gost;
import com.example.demo.Service.krepezhService;

@RestController
@RequestMapping("/restapi")
public class krepezhController {

    @Autowired
    private krepezhService service;

    @GetMapping("/all")
    public List<Map<String, Object>> getAll() {
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public Map<String, Object> getOne(@PathVariable Integer id) {
        return service.getOne(id);
    }

    @GetMapping("/search")
    public List<gost> search(@RequestParam Map<String, String> params) {
        return service.search(params);
    }

    @PostMapping("/add")
    public void add(@RequestBody gost gost) throws Exception {
        service.add(gost);
    }

    @PutMapping("/update/{id}")
    public void update(@PathVariable Integer id, @RequestBody gost gost) throws Exception {
        service.update(id, gost);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}