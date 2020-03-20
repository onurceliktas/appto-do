package com.toDoListApp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.toDoListApp.model.Task;
import com.toDoListApp.model.User;

public class ToDoUtil {
	
	public static Long USER_ROLE_ID = new Long(2);

	public static Date stringToDate(String string, String format) throws ParseException {
		SimpleDateFormat _format = new SimpleDateFormat(format);
		return _format.parse(format);
	}
	
	public static Date formatDate(Date date, String formatType){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatType);
		try {
			return simpleDateFormat.parse(simpleDateFormat.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<Task> setTaskListEditableField(List<Task> tasks, User loggedInUser){
		tasks.forEach(t -> t.setEditable(loggedInUser.equals(t.getOwner())));
		return tasks;
	}
	
}
