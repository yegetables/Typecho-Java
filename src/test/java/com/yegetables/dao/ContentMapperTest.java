package com.yegetables.dao;

import com.yegetables.pojo.Content;
import com.yegetables.pojo.ContentStatus;
import com.yegetables.pojo.ContentType;
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
        var content = contentMapper.getContent(RandomTools.getRandom(contentMapper.getAllContents()).cid());
        System.out.println(content);
    }

    @Test
    void getContentsByAuthorId() {
        var t = RandomTools.getRandom(contentMapper.getAllContents());
        t.toString();
        var content = contentMapper.getContentsByAuthorId(t.author().getUid());
        System.out.println(content);
    }

    @Test
    void getContentsByType() {
        var content = contentMapper.getContentsByType(ContentType.post);
        System.out.println(content);
    }

    @Test
    void getContentsByStatus() {
        var content = contentMapper.getContentsByStatus(ContentStatus.PRIVATE);
        System.out.println(content);
    }

    Content createContent() {
        Content content = new Content();
        content.text(RandomTools.getRandomText(8));
        content.title(RandomTools.getRandomName());
        content.created(TimeTools.NowTime());
        content.modified(content.created());
        content.author(RandomTools.getRandom(userMapper.getAllUsers()));
        content.parent(RandomTools.getRandom(contentMapper.getAllContents()));
        content.status(ContentStatus.PRIVATE);
        assertEquals(1, contentMapper.addContent(content));
        return content;
    }

    @Test
    void addContent() {
        Content content = createContent();
        var newContent = contentMapper.getContent(content.cid());
        //        newContent.toString();
        assertEquals(newContent, content);
    }


    @Test
    void updateContent() {
        Content content = createContent();
        content.text(RandomTools.getRandomText(8));
        content.status(ContentStatus.publish);
        assertEquals(1, contentMapper.updateContent(content));
        var newContent = contentMapper.getContent(content.cid());
        //        newContent.toString();
        assertEquals(newContent, content);
        assertEquals(1, contentMapper.deleteContent(newContent));
    }

    @Test
    void deleteContent() {
        Content content = createContent();
        assertEquals(1, contentMapper.deleteContent(content));
        Content newContent = contentMapper.getContent(content.cid());
        assertNull(newContent);

    }


}