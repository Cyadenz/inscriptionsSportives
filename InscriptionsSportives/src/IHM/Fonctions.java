package IHM;

import java.text.ParseException;



class Fonctions extends FenêtrePrincipal{
	
	public Fonctions() throws ParseException {
		super();
	}
	  
	private static final long serialVersionUID = 1L;

	public static String encrypt(String password){
	        String crypte="";
	        for (int i=0; i<password.length();i++)  {
	            int c=password.charAt(i)^48;  
	            crypte=crypte+(char)c; 
	        }
	        return crypte;
	    }
	  
	public static String decrypt(String password){
	        String aCrypter="";
	        for (int i=0; i<password.length();i++)  {
	            int c=password.charAt(i)^48;  
	            aCrypter=aCrypter+(char)c; 
	        }
	        return aCrypter;
	    }
	    
}
