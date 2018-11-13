package io.pumelo.alioss.controller;

import io.pumelo.alioss.service.AliOssService;
import io.pumelo.alioss.vo.AliOssTokenVo;
import io.pumelo.common.basebean.ImageType;
import io.pumelo.common.web.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * author Pumelo
 * created at 2017/9/16.
 */
@RestController
@Api(description = "阿里云OSS签名")
public class AliOssController {
    @Autowired
    private AliOssService aliOssService;

    @PostMapping("/ali_oss")
    @ApiOperation(value = "获取阿里OSS签名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imageType",value = "图片种类",required = true,paramType = "form",dataType = "string")
    })
    public ApiResponse<AliOssTokenVo> getOSSToken(@RequestParam("imageType") ImageType imageType) throws UnsupportedEncodingException {
        return aliOssService.getOSSToken(imageType);
    }

}
