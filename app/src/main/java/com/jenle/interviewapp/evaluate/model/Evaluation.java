package com.jenle.interviewapp.evaluate.model;

import java.io.Serializable;

/**
 * Models an interview evaluation
 * Created by Jenle Samuel on 12/3/16.
 */

public class Evaluation implements Serializable{

    private String candidateName;
    private String jobTitle;
    private String interviewerName;
    private String interviewDate;
    private int communication;
    private int problemSolving;
    private int conflictResolution;
    private int teamWork;
    private int workExperience;
    private int educationalBackground;
    private int leadership;
    private String comments;

    public Evaluation(String candidateName, String jobTitle, String interviewerName,
                      String interviewDate, int communication, int problemSolving, int conflictResolution,
                      int teamWork, int workExperience, int educationalBackground, int leadership,
                      String comments) {
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.interviewerName = interviewerName;
        this.interviewDate = interviewDate;
        this.communication = communication;
        this.problemSolving = problemSolving;
        this.conflictResolution = conflictResolution;
        this.teamWork = teamWork;
        this.workExperience = workExperience;
        this.educationalBackground = educationalBackground;
        this.leadership = leadership;
        this.comments = comments;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getInterviewerName() {
        return interviewerName;
    }

    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }

    public String getInterviewDate() {
        return interviewDate;
    }

    public void setInterviewDate(String interviewDate) {
        this.interviewDate = interviewDate;
    }

    public int getCommunication() {
        return communication;
    }

    public void setCommunication(int communication) {
        this.communication = communication;
    }

    public int getProblemSolving() {
        return problemSolving;
    }

    public void setProblemSolving(int problemSolving) {
        this.problemSolving = problemSolving;
    }

    public int getConflictResolution() {
        return conflictResolution;
    }

    public void setConflictResolution(int conflictResolution) {
        this.conflictResolution = conflictResolution;
    }

    public int getTeamWork() {
        return teamWork;
    }

    public void setTeamWork(int teamWork) {
        this.teamWork = teamWork;
    }

    public int getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(int workExperience) {
        this.workExperience = workExperience;
    }

    public int getEducationalBackground() {
        return educationalBackground;
    }

    public void setEducationalBackground(int educationalBackground) {
        this.educationalBackground = educationalBackground;
    }

    public int getLeadership() {
        return leadership;
    }

    public void setLeadership(int leadership) {
        this.leadership = leadership;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
