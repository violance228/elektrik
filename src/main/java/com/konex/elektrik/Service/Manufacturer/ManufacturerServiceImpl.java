package com.konex.elektrik.Service.Manufacturer;//package com.konex.elektrik.Service.Manufacturer;
//
//import com.konex.elektrik.Entity.Manufacturer;
//import com.konex.elektrik.Repository.ManufacturerRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//public class ManufacturerServiceImpl implements ManufacturerService {
//
//    @Autowired
//    private ManufacturerRepository manufacturerRepository;
//
//    @Transactional
//    public Manufacturer addManufacturer(Manufacturer manufacturer) {
//
//        return manufacturerRepository.save(manufacturer);
//    }
//
//    @Transactional
//    public void delete(Long id) {
//
//        manufacturerRepository.delete(id);
//    }
//
//    @Transactional(readOnly = true)
//    public Manufacturer getById(Long id) {
//
//        return manufacturerRepository.findOne(id);
//    }
//
//    @Transactional(readOnly = true)
//    public Manufacturer findByName(String name) {
//
//        return manufacturerRepository.findByName(name);
//    }
//
//    @Transactional(readOnly = true)
//    public Manufacturer findById(Long id) {
//
//        return manufacturerRepository.findById(id);
//    }
//
//    @Transactional
//    public Manufacturer editManufacturer(Manufacturer manufacturer) {
//
//        return manufacturerRepository.saveAndFlush(manufacturer);
//    }
//
//    @Transactional(readOnly = true)
//    public List<Manufacturer> getAll(Sort sort) {
//
//        return manufacturerRepository.findAll();
//    }
//}
