package com.itc.controller;


import com.itc.domain.Answer;
import com.itc.domain.Question;
import com.itc.service.QuestionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


@RestController
public class QuestionController {
    private QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/question/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.findById(id).get();
    }

    @PostMapping(value = "/question", consumes = "application/json", produces = "application/json")
    public Question addQuestion(@RequestBody Question question) {
        questionService.save(question);
        return question;
    }

    @DeleteMapping(value = "/question/{id}", consumes = "application/json", produces = "application/json")
    public Question deleteQuestion(@PathVariable Long id) {
        questionService.deleteById(id);
        return null;
    }

    @PatchMapping(value = "/question/{id}", consumes = "application/json", produces = "application/json")
    public Question updateQuestion(@PathVariable Long id, @RequestBody Question newQuestion) {
        Question questionFromDb = questionService.findById(id).get();
        if (questionFromDb != null) {
            questionFromDb = questionService.findById(id).get();
            BeanUtils.copyProperties(newQuestion, questionFromDb);
            questionService.save(questionFromDb);
            return questionFromDb;
        }
        return null;
    }

    @PostMapping(value = "/???", consumes = "application/json", produces = "application/json")
    public boolean checkTest(@RequestBody Question question, @RequestBody Set<Answer> answers) {
        return questionService.checkTest(question, answers);
    }
}