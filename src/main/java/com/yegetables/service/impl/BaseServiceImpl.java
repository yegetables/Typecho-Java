package com.yegetables.service.impl;

import com.yegetables.dao.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope
public class BaseServiceImpl {
    //    protected static Logger log;
    protected static final Logger log = LogManager.getLogger(BaseServiceImpl.class);

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
