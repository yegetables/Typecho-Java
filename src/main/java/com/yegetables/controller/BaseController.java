package com.yegetables.controller;

import com.yegetables.service.MetaService;
import com.yegetables.service.UserService;
import com.yegetables.utils.JsonTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    protected static final Logger log = LogManager.getLogger(BaseController.class);
    @Autowired
    protected UserService userService;
    @Autowired
    protected MetaService metaService;

    @Autowired
    protected JsonTools jsonTools;

}
