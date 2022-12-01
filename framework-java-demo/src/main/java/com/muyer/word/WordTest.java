package com.muyer.word;

import com.google.common.collect.Lists;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Description:
 * date: 2021/1/4 18:56
 *
 * @author YeJiang
 */
public class WordTest {
    public static void main(String[] args) {
        wordAdd();
    }

    private static void wordAdd() {
        List<Question> list = new ArrayList<>();
        try {
            FileInputStream inputStream = new FileInputStream(new File("E:" + File.separator + "色彩性格测试题(最新）.docx"));
            XWPFDocument xwpfDocument = new XWPFDocument(inputStream);
            XWPFParagraph para;
            Iterator<XWPFParagraph> iterator = xwpfDocument.getParagraphsIterator();
            List<String> questions = new ArrayList<>();
            while (iterator.hasNext()) {
                para = iterator.next();
                if (para.getText().trim().length() == 0) {
                    continue;
                }
                questions.add(para.getText().trim());
            }
            Question q = null;
            for (int i = 0; i < questions.size(); i++) {
                if (i % 5 == 0) {
                    q = new Question();
                    q.setState(1);
                    q.setQuestionType(0);
                    q.setSubjectId(27);
                    q.setContestId(8);
                    q.setCreateTime(new Date());
                    q.setUpdateTime(new Date());
                    q.setTitle(questions.get(i));
                    q.setContent(questions.get(i));
                } else if (i % 5 == 1) {
                    q.setOptionA(questions.get(i));
                }else if (i % 5 == 2) {
                    q.setOptionB(questions.get(i));
                }else if (i % 5 == 3) {
                    q.setOptionC(questions.get(i));
                }else if (i % 5 == 4) {
                    q.setOptionD(questions.get(i));
                    list.add(q);
                }
            }
            System.out.println(list);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
