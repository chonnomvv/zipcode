package kr.co.bit.handler;

import javax.servlet.http.HttpServletRequest;

public interface Command {
	public String process(HttpServletRequest request);
}
