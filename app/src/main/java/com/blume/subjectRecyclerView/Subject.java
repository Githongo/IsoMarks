package com.blume.subjectRecyclerView;

public class Subject {
    private String subjectId;
    private String subjectName;
    private String stream;
    private String examType;
    private String status;

    public Subject(String SubjectID, String SubjectName, String Stream, String ExamType, String Status){
        this.subjectId = SubjectID;
        this.subjectName = SubjectName;
        this.stream = Stream;
        this.examType = ExamType;
        this.status = Status;

    }

    public String getSubjectId(){
        return subjectId;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName(){
        return subjectName;}

    public String getStream(){
        return stream;
    }
    public String getExamType(){
        return examType;
    }
    public String getStatus(){
        return status;
    }

}
