package com.itc.controller;

import com.itc.dao.AnswerDao;
import com.itc.domain.Answer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnswerController {
    private AnswerDao answerDao;

    @Autowired
    public AnswerController(AnswerDao answerDao) {
        this.answerDao = answerDao;
    }

    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable Long id){
        return answerDao.findById(id).get();
    }

    @GetMapping(value = "/answer", produces = "application/json")
    public Iterable<Answer> getAnswers (){
        return answerDao.findAll();
    }

    @PostMapping(value = "/answer", consumes = "application/json", produces = "application/json")
    public Answer addAnswer(@RequestBody Answer answer){
        answerDao.save(answer);
        return answer;
    }

    @PatchMapping(value = "/answer/{id}", consumes = "application/json", produces = "application/json")
    public Answer updateAnswer(@PathVariable Long id, @RequestBody Answer newAnswer){
        Answer answerFromDb = answerDao.findById(id).get();
        if (answerFromDb != null){
            answerFromDb = answerDao.findById(id).get();
            BeanUtils.copyProperties(newAnswer, answerFromDb);
            answerDao.save(answerFromDb);
            return answerFromDb;
        }
        return null;
    }

    @DeleteMapping(value = "/answer/{id}", consumes = "application/json", produces = "application/json")
    public void deleteAnswer(@PathVariable Long id){
        answerDao.deleteById(id);
    }

}
