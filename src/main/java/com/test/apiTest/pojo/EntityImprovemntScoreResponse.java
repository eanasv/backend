package com.test.apiTest.pojo;

public class EntityImprovemntScoreResponse {
    private String entityName;
    private double avgScoreDiff;
    private int rank;

    public EntityImprovemntScoreResponse(String entityName, double avgScoreDiff, int rank) {
        this.entityName = entityName;
        this.avgScoreDiff = avgScoreDiff;
        this.rank = rank;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public double getAvgScoreDiff() {
        return avgScoreDiff;
    }

    public void setAvgScoreDiff(double avgScoreDiff) {
        this.avgScoreDiff = avgScoreDiff;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
