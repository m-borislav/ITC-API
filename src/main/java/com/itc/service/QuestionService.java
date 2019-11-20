package com.itc.service;

import com.itc.dao.AnswerDao;
import com.itc.dao.QuestionDao;
import com.itc.domain.Answer;
import com.itc.domain.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class QuestionService {
    private QuestionDao questionDao;
    private AnswerDao answerDao;

    @Autowired
    public QuestionService(QuestionDao questionDao, AnswerDao answerDao) {
        this.questionDao = questionDao;
        this.answerDao = answerDao;
    }

    public boolean checkTest(Question question, Set<Answer> answers) {
        Answer trueAnswer = null;
        for (Answer userAnswer : question.getAnswers()) {
            if (userAnswer.isCorrect()) {
                trueAnswer = userAnswer;
            }
        }
        return (answers.contains(trueAnswer));
    }

    public Optional<Question> findById(Long id) {
        return questionDao.findById(id);
    }

    public Question save(Question question) {
        return questionDao.save(question);
    }

    public void deleteById(Long id){
        questionDao.deleteById(id);
    }


}
