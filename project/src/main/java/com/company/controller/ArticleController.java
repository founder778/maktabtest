package com.company.controller;

import com.company.dto.ArticleDto;
import com.company.service.ArticleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@Api(tags = "Article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    //    ***
//    NEW ARTICLE CREATE
   //    ***
    @PostMapping("/create")
    @ApiOperation(value = "new article create post method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = ArticleDto.class,responseContainer = "List")
    })
    public ResponseEntity<?> create(@RequestBody ArticleDto dto) {
        ArticleDto article = articleService.create(dto);
        return ResponseEntity.ok(article);
    }

    //    ***
//    GET ALL ARTICLE
   //    ***
    @GetMapping("/all/{size}/{page}")
    @ApiOperation(value = "get all article paging method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = ArticleDto.class,responseContainer = "List")
    })
    public ResponseEntity<?> getAllArticle(@ApiParam(name = "size",required = true) @PathVariable("size") Integer size,
                                           @ApiParam(name = "paging",required = true) @PathVariable("page") Integer page) {
        PageImpl<ArticleDto> article = articleService.getAllArticle(size, page);
        return ResponseEntity.ok(article);
    }

    //    ***
//    GET  ARTICLE BY ID
   //    ***
    @GetMapping("/get/{jwt}")
    @ApiOperation(value = "get byId method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = ArticleDto.class,responseContainer = "ArticleDto")
    })
    public ResponseEntity<?> getById(@ApiParam(name = "jwt") @PathVariable("jwt") String jwt) {
        ArticleDto response = articleService.getById(jwt);
        return ResponseEntity.ok(response);
    }
    //    ***
//    DELETE ARTICLE BY ID
    //    ***
    @DeleteMapping("/delete/{jwt}")
    @ApiOperation(value = "delete byId method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = String.class,responseContainer = "String")
    })
    public ResponseEntity<?> deleteById(@ApiParam(name = "jwt") @PathVariable("jwt") String jwt) {
        String response = articleService.deleteById(jwt);
        return ResponseEntity.ok(response);
    }

    //    ***
//    UPDATE ARTICLE BY ID
    //    ***
    @PostMapping("/update")
    @ApiOperation(value = "update byId post method")
    @ApiResponses(value = {
            @ApiResponse( code =200,message="succesful",response = String.class,responseContainer = "String")
    })
    public ResponseEntity<?> updateById(@RequestBody  ArticleDto dto){
        String response = articleService.updateById(dto);
        return ResponseEntity.ok(response);
    }


}
