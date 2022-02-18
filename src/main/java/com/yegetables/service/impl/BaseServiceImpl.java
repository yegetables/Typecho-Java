package com.yegetables.service.impl;

import com.yegetables.dao.*;
import org.springframework.beans.factory.annotation.Autowired;


public class BaseServiceImpl {
    protected static final org.apache.logging.log4j.Logger log = org.apache.logging.log4j.LogManager.getLogger(BaseServiceImpl.class);
    @Autowired
    protected UserMapper userMapper;
    @Autowired
    protected RelationshipMapper relationshipMapper;
    @Autowired
    protected OptionMapper optionMapper;
    @Autowired
    protected MetaMapper metaMapper;
    @Autowired
    protected FieldMapper fieldMapper;
    @Autowired
    protected ContentMapper contentMapper;
    @Autowired
    protected CommentMapper commentMapper;

}
