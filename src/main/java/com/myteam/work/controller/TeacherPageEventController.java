package com.myteam.work.controller;

import java.util.LinkedList;
import java.util.List;

import com.myteam.work.gui.pages.TeacherPage;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.handler.SemesterHandler;
import com.myteam.work.management.handler.StudentHandler;
import com.myteam.work.management.handler.SubjectHandler;
import com.myteam.work.management.handler.TeachClassHandler;

import lombok.extern.slf4j.Slf4j;
// import com.myteam.work.management.handler.TeacherHandler;


@Slf4j
public class TeacherPageEventController {
	private static TeacherPageEventController tpec;
	private SubjectHandler sh;
	private StudentHandler sth;
	// private TeacherHandler th;
	private TeachClassHandler tch;
	private SemesterHandler seh;

	private TeacherPageEventController() {
		this.sh = new SubjectHandler();
		this.sth = new StudentHandler();
		// this.th = new TeacherHandler();
		this.tch = new TeachClassHandler();
		this.seh = new SemesterHandler();
	}

	public static TeacherPageEventController getController() {
		if(tpec == null) tpec = new TeacherPageEventController();

		return tpec;
	}

	public void loadAllSubject() {
		log.info("Load all subject");
		var table = ((TeacherPage) TeacherPage.getPage()).getSubjectTable();
		table.clearData();
		var subjects = this.sh.getAllSubject();

		if(subjects == null) return;

		table.addData(loadSubject(subjects));
	}

	public void searchSubject(String s) {
		log.info("Search subject: " + s);
		var table = ((TeacherPage) TeacherPage.getPage()).getSubjectTable();
		table.clearData();
		var subjects = this.sh.getSubject(s);

		if(subjects == null) return;

		table.addData(loadSubject(subjects));
	}

	public void loadSemester() {
		var semesters = this.seh.getAllSemester();
		var selector = ((TeacherPage) TeacherPage.getPage()).getSemesterSelector();
		selector.removeAllItems();
		selector.addItem(null);

		if(semesters == null) return;

		for(Semester sm : semesters) selector.addItem(sm);
	}

	public void loadTeachClass(Semester sm) {
		var selector = ((TeacherPage) TeacherPage.getPage()).getClassSelector();
		selector.removeAllItems();
		selector.addItem(null);

		if(sm == null) return;

		//var class = this.tch.getClass(sm.getYears(), LoginController.getController().getCurrentUser().getId());
		var clazz = this.tch.getClass(sm.getYears(), LoginController.getController().getCurrentUser().getId());

		if(clazz == null) return;

		for(TeachClass tc : clazz) selector.addItem(tc);
	}

	public void loadStudentInTeachClass(TeachClass tc) {
		
	}

	private Object[][] loadSubject(List<Subject> subjects) {
		List<Object[]> data = new LinkedList<>();

		for(Subject subject : subjects) {
			var id = subject.getId();
			var prerequisites = sh.getPrerequistes(id);
			var prerequisitesName = new String[prerequisites == null ? 0 : prerequisites.size()];

			for(var i = 0; i < prerequisitesName.length; i++) prerequisitesName[i] = sh.getName(prerequisites.get(i));

			var subjectRow = new Object[5];
			subjectRow[0] = id;
			subjectRow[1] = subject.getSubjectName();
			subjectRow[2] = prerequisitesName;
			subjectRow[3] = subject.getCredits();
			subjectRow[4] = subject.isRequired() ? "yes" : "no";
			data.add(subjectRow);
		}

		return data.toArray(Object[][]::new);
	}
}
