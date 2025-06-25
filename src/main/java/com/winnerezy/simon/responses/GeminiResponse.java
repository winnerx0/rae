package com.winnerezy.simon.responses;

import lombok.Data;
import java.util.List;

@Data
public class GeminiResponse {
    private List<Candidate> candidates;
    private UsageMetadata usageMetadata;
    private String modelVersion;
    private String responseId;

    @Data
    public static class Candidate {
        private Content content;
        private String finishReason;
        private double avgLogprobs;
    }

    @Data
    public static class Content {
        private List<Part> parts;
        private String role;
    }

    @Data
    public static class Part {
        private String text;
    }

    @Data
    public static class UsageMetadata {
        private int promptTokenCount;
        private int candidatesTokenCount;
        private int totalTokenCount;
        private List<PromptTokenDetail> promptTokensDetails;
        private List<CandidateTokenDetail> candidatesTokensDetails;
    }

    @Data
    public static class PromptTokenDetail {
        private String modality;
        private int tokenCount;
    }

    @Data
    public static class CandidateTokenDetail {
        private String modality;
        private int tokenCount;
    }
}
