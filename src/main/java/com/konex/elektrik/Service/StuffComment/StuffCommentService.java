/*
 * Copyright (c) 2018. month - 5. day - 7.
 * @Author Violence
 * @All rights reserved
 */

package com.konex.elektrik.Service.StuffComment;

import com.konex.elektrik.Entity.Stuff;
import com.konex.elektrik.Entity.StuffComment;
import com.konex.elektrik.Entity.User;

import java.util.List;

public interface StuffCommentService {

    StuffComment addStuffComment(StuffComment stuffComment, Stuff stuff, User user);
    void delete(Long id);
    StuffComment getById(Long id);
    StuffComment editStuffComment(StuffComment stuffComment);
    List<StuffComment> getAll();
}
