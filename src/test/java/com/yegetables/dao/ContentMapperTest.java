package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.utils.BaseJunit5Test;
import com.yegetables.utils.RandomTools;
import com.yegetables.utils.TimeTools;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@Log4j2
class ContentMapperTest extends BaseJunit5Test {

    @Autowired
    private ContentMapper contentMapper;

    @Autowired
    private UserMapper userMapper;


    @Test
    void getAllContents() {
        var list = contentMapper.getAllContents();
        list.forEach(System.out::println);
    }

    @Test
    void getContent() {
        var content = contentMapper.getContent(2L);
        System.out.println(content);
    }

    @Test
    void getContentsByAuthorId() {
        var content = contentMapper.getContentsByAuthorId(1L);
        System.out.println(content);
    }

    @Test
    void getContentsByType() {
        var content = contentMapper.getContentsByType("page");
        System.out.println(content);
    }

    @Test
    void getContentsByStatus() {
        var content = contentMapper.getContentsByStatus("publish");
        System.out.println(content);
    }

    Content createContent() {
        Content content = new Content();
        content.setText(RandomTools.getRandomText(8));
        content.setTitle(RandomTools.getRandomName());
        content.setCreated(TimeTools.NowTime());
        content.setModified(content.getCreated());
        content.setAuthor(RandomTools.getRandom(userMapper.getAllUsers()));
        content.setParent(RandomTools.getRandom(contentMapper.getAllContents()));
        contentMapper.addContent(content);
        return content;
    }

    @Test
    void addContent() {
        Content content = createContent();
        var newContent = contentMapper.getContent(content.getCid());
        assertEquals(content, newContent);
    }


    @Test
    void updateContent() {
        Content content = createContent();
        content.setText(RandomTools.getRandomText(8));
        contentMapper.updateContent(content);
        var newContent = contentMapper.getContent(content.getCid());
        assertEquals(content, newContent);
        contentMapper.deleteContent(newContent);
    }

    @Test
    void deleteContent() {
        Content content = createContent();
        contentMapper.deleteContent(content);
        Content newContent = contentMapper.getContent(content.getCid());
        assertNull(newContent);

    }


}