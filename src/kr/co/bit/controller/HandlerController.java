package kr.co.bit.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.jrockit.jfr.RequestDelegate;

import kr.co.bit.handler.Command;

public class HandlerController extends HttpServlet {
	private HashMap<String, Command> map;

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("1번");
		map = new HashMap<String,Command>();
		String path = getServletContext().getRealPath("WEB-INF/file/commandList.txt");
		File file = new File(path);
		FileReader fr = null;
		BufferedReader br = null;
		String line = null;
		try {
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			while((line=br.readLine())!=null) {
				System.out.println(line);
				String[] temp = line.split("=");
				Object obj = Class.forName(temp[1]).newInstance();
				Command cmd = (Command)obj;
				map.put(temp[0], cmd);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				br.close();
				fr.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		System.out.println("init end");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("doPost 2번");
		req.setCharacterEncoding("UTF-8");
		
		String url = req.getServletPath();
		System.out.println("in "+url);
		Command cmd = map.get(url);
		
		String toUrl = cmd.process(req);
		RequestDispatcher rd = req.getRequestDispatcher(toUrl);
		System.out.println("doPost 나감");
		rd.forward(req, resp);
	}
}
