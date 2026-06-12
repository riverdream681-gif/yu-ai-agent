package com.yupi.yuaiagent.rag;

import com.yupi.yuaiagent.tools.TransApi;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TranslationQueryTransformer {

    @Value("${app.config.app-id}")
    private String appId;

    @Value("${app.config.security-key}")
    private String securityKey;


    /**
     * 转换/翻译查询词
     * @param originalQuery 用户的原始提问
     * @return 翻译后的提问
     */
    public String transform(String originalQuery) {
        if (!StringUtils.hasText(originalQuery)) {
            return originalQuery;
        }

        // 1. 语种检测 (可选优化：如果是纯英文，为了节省API调用，可直接放行)
        if (isPureEnglish(originalQuery)) {
            return originalQuery;
        }

        // 假设这是你封装的第三方翻译 API 客户端（如调用百度翻译/DeepL）
        TransApi transApi = new TransApi(appId, securityKey);

        // 2. 调用第三方翻译 API，将用户的输入（如中文）翻译成文档的语言（如英文）
        try {
            // 参数示例：(原文, 源语言, 目标语言)
            String translatedQuery = transApi.getTransResult(originalQuery, "auto", "en");
            
            // 可以选择增加日志，方便后期调试翻译质量
            System.out.println("Original Query: " + originalQuery + " -> Translated: " + translatedQuery);
            
            return translatedQuery;
        } catch (Exception e) {
            // 3. 容错处理：如果第三方 API 挂了，降级返回原词，或者走内置的大模型备用方案
            // log.error("Translation API failed", e);
            return originalQuery; 
        }
    }

    // 一个简单的纯英文判断逻辑（根据业务需要调整）
    private boolean isPureEnglish(String text) {
        return text.matches("^[a-zA-Z0-9\\s\\p{Punct}]+$");
    }
}