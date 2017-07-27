package br.com.image.util;

import br.com.image.exception.AppException;

public class Validate {

	public Validate() {
	}

	/***********************************************************************/
	public static void isNull(Object data, String key) throws AppException {
		if (data == null)
			throw new AppException(key);
		else
			return;
	}

	/***********************************************************************/
	public static void isEmpty(String data, String key) throws AppException {
		if (data != null) {
			if (data.trim().isEmpty())
				throw new AppException(key);
			else
				return;
		} else {
			throw new AppException(key);
		}
	}

}
