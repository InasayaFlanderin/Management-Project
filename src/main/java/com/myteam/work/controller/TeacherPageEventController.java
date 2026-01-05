package com.myteam.work.controller;

import java.util.HashMap;
import java.util.Map;

import com.myteam.work.gui.pages.TeacherPage;
import com.myteam.work.management.data.DataTableParser;
import com.myteam.work.management.data.Pair;
import com.myteam.work.management.data.Semester;
import com.myteam.work.management.data.Subject;
import com.myteam.work.management.data.TeachClass;
import com.myteam.work.management.handler.SemesterHandler;
import com.myteam.work.management.handler.StudentHandler;
import com.myteam.work.management.handler.SubjectHandler;
import com.myteam.work.management.handler.TeachClassHandler;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class TeacherPageEventController {
	private static TeacherPageEventController tpec;
	private final SubjectHandler sh;
	private final StudentHandler sth;
	private final TeachClassHandler tch;
	private final SemesterHandler seh;
	private final DataTableParser parser;
	private final HashMap<Pair<Integer, Integer>, Object> changeRecorder;

	private TeacherPageEventController() {
		this.sh = new SubjectHandler();
		this.sth = new StudentHandler();
		this.tch = new TeachClassHandler();
		this.seh = new SemesterHandler();
		this.parser = new DataTableParser();
		this.changeRecorder = new HashMap<>();
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

		table.addData(this.parser.parseSubjectFetchPrerequisites(subjects));
	}

	public void searchSubject(String s) {
		var table = ((TeacherPage) TeacherPage.getPage()).getSubjectTable();
		table.clearData();
		var subjects = this.sh.getSubject(s);

		if(subjects == null) return;

		table.addData(this.parser.parseSubjectFetchPrerequisites(subjects));
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

	public void loadStudentInTeachClass(TeachClass tc) {
		var studentTable = ((TeacherPage) TeacherPage.getPage()).getStudentTable();
		studentTable.clearData();

		if(tc == null)	return;
		var studentList = this.sth.loadStudentListInfo(tc.getId());

		if(studentList == null) return;

		var data = this.parser.parseInfoFetchData(studentList);

		if(data == null) return;

		studentTable.addData(data);
	}

	public HashMap<Pair<Integer, Integer>, Object> getRecorder() {
		return this.changeRecorder;
	}	

	@SuppressWarnings("unchecked")
	public void submit(TeachClass tc) {
		var iterator = this.changeRecorder.entrySet().iterator();

		while(iterator.hasNext()) {
			var entry = (Map.Entry) iterator.next();
			var key = (Pair<Integer, Integer>) entry.getKey();

			/*if(key.second() == 3) {
				this.tch.submit1((Float) entry.getValue(), key.first(), tc.getId());
			} else if(key.second() == 4) {
				this.tch.submit2((Float) entry.getValue(), key.first(), tc.getId());
			} else if(key.second() == 5) {
				this.tch.endtest((Float) entry.getValue(), key.first(), tc.getId());
			} else {
				log.error("System has a breach");
				System.exit(0);
			}*/

			switch(key.second()) {
				case 3:
					this.tch.submit1((Float) entry.getValue(), key.first(), tc.getId());
					break;
				case 4:
					this.tch.submit2((Float) entry.getValue(), key.first(), tc.getId());
					break;
				case 5:
					this.tch.endtest((Float) entry.getValue(), key.first(), tc.getId());
					break;
				default:
					log.error("System has a breach");
					System.exit(0);
			}

			this.sth.updateStudentGpa(key.first());
		}

		this.tch.updateClassGpa(tc.getId());
		loadStudentInTeachClass(tc);
	}		
}
