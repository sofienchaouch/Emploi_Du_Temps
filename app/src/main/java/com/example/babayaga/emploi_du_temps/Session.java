package com.example.babayaga.emploi_du_temps;

public class Session {
    private String subject ;
    private String actor ;
    private String salle ;
    private String time ;

    public Session(String subject, String actor, String salle , String time) {
        this.subject = subject;
        this.actor = actor;
        this.salle = salle;
        this.time = time;
    }

    public String getSubject() {
        return subject;
    }

    public String getActor() {
        return actor;
    }

    public String getSalle() {
        return salle;
    }

    public String getTime() {
        return time;
    }
}
