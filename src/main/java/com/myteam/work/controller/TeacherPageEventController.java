package com.myteam.work.controller;

import com.myteam.work.gui.pages.TeacherPage;
import com.myteam.work.management.data.DataTableParser;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.handler.SemesterHandler;
import com.myteam.work.management.handler.StudentHandler;
import com.myteam.work.management.handler.SubjectHandler;
import com.myteam.work.management.handler.TeachClassHandler;
import com.myteam.work.management.handler.TeacherHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TeacherPageEventController {
	private static TeacherPageEventController tpec;
	private SubjectHandler sh;
	private StudentHandler sth;
	private TeacherHandler th;
	private TeachClassHandler tch;
	private SemesterHandler seh;

	private TeacherPageEventController() {
		this.sh = new SubjectHandler();
		this.sth = new StudentHandler();
		this.th = new TeacherHandler();
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

		table.addData(DataTableParser.parseSubjectFetchPrerequisites(subjects));
	}

	public void searchSubject(String s) {
		log.info("Search subject: " + s);
		var table = ((TeacherPage) TeacherPage.getPage()).getSubjectTable();
		table.clearData();
		var subjects = this.sh.getSubject(s);

		if(subjects == null) return;

		table.addData(DataTableParser.parseSubjectFetchPrerequisites(subjects));
}

	public void loadTeacherSubject() {
		var subjects = this.sh.loadTeacherSubject(LoginController.getController().getCurrentUser().getId());
		var selector = ((TeacherPage) TeacherPage.getPage()).getSubjectSelector();
		selector.removeAllItems();
		selector.addItem(null);

		if(subjects == null) return;

		for(Subject subject : subjects) selector.addItem(subject);
	}

	public void loadSemester() {
		var semesters = this.seh.getAllSemester();
		var selector = ((TeacherPage) TeacherPage.getPage()).getSemesterSelector();
		selector.removeAllItems();
		selector.addItem(null);

		if(semesters == null) return;

		for(Semester sm : semesters) selector.addItem(sm);
	}

	public void loadTeachClass(Semester sm, Subject s) {
		var selector = ((TeacherPage) TeacherPage.getPage()).getClassSelector();
		selector.removeAllItems();
		selector.addItem(null);

		if(sm == null || s == null) return;

		var clazz = this.tch.getClass(sm.getId(), LoginController.getController().getCurrentUser().getId(), s.getId());

		if(clazz == null) return;

		for(TeachClass tc : clazz) selector.addItem(tc);
	}

	public void loadStudentInTeachClass(Semester s, TeachClass tc, Subject sj) {
		if (s == null || tc == null || sj == null) {
			log.info("Skipping loadStudentInTeachClass due to null parameter(s): semester={}, class={}, subject={}", s, tc, sj);
			var studentTable = ((TeacherPage) TeacherPage.getPage()).getStudentTable();
			studentTable.clearData();
			return;
		}

		log.info("load student list in: {} of {} in {}", tc, sj, s);
		var studentTable = ((TeacherPage) TeacherPage.getPage()).getStudentTable();
		studentTable.clearData();
		var studentList = this.sth.loadStudentListInfo(s.getId(), tc.getId(), sj.getId());

		if (studentList == null) return;

		studentTable.addData(DataTableParser.parseInfoFetchData(studentList));
	}

	
}
