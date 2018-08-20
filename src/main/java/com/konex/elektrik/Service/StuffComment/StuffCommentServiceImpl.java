/*
 * Copyright (c) 2018. month - 5. day - 7.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.StuffComment;

import com.konex.elektrik.Entity.Stuff;
import com.konex.elektrik.Entity.StuffComment;
import com.konex.elektrik.Entity.User;
import com.konex.elektrik.Repository.StuffCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StuffCommentServiceImpl implements StuffCommentService {

    @Autowired
    private StuffCommentRepository stuffCommentRepository;


    @Transactional
    public StuffComment addStuffComment(StuffComment stuffComment, Stuff stuff, User user) {

        stuffComment.setStuffs(stuff);
        stuffComment.setUsers(user);
        return stuffCommentRepository.save(stuffComment);
    }

    @Transactional
    public void delete(Long id) {

        stuffCommentRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public StuffComment getById(Long id) {

        return stuffCommentRepository.getOne(id);
    }

    @Transactional
    public StuffComment editStuffComment(StuffComment stuff) {

        return stuffCommentRepository.saveAndFlush(stuff);
    }

    @Transactional(readOnly = true)
    public List<StuffComment> getAll() {

        return stuffCommentRepository.findAll();
    }
}
