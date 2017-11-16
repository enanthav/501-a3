/*==========================================================================
File: ObjectInspector.java
Purpose:Demo Object inspector for the Asst2TestDriver
Location: University of Calgary, Alberta, Canada
Created By: Jordan Kidney
Created on:  Oct 23, 2005
Last Updated: Oct 23, 2005
***********************************************************************
If you are going to reproduce this code in any way for your asignment 
rember to include my name at the top of the file toindicate where you
got the original code from
***********************************************************************
========================================================================*/

import java.util.*;
import java.lang.reflect.*;
import static java.lang.System.out;



public class ObjectInspector
{
    private static final String fmt = "%11s: %s%n";
    private boolean recursive; 
    public ObjectInspector() { }


    //-----------------------------------------------------------
    public void inspect(Object obj, boolean recursive)
    {
	    	if (obj != null ) {
	    		this.recursive = recursive;
			Vector objectsToInspect = new Vector();
			Class ObjClass = obj.getClass();
			inspectClass(ObjClass);
			if (obj.getClass().isArray()) {
				inspectArray(obj, ObjClass);
			}
			inspectMethods(ObjClass);
			inspectConstructors(ObjClass);
			inspectFields(obj, ObjClass, objectsToInspect);

			if(recursive)
			    inspectFieldClasses( obj, ObjClass, objectsToInspect, recursive); 
		
	    	} else {
	    		System.out.println("No object found");
	    		recursive = false;
	    	}
    }


    // Print out current class name and superclass name 
    public void inspectClass(Class objClass) {
    		System.out.println("\n-------- Current Class --------");
    		out.format(fmt, "Declaring class", objClass.getSimpleName()); // print name of declaring class
	    	out.format(fmt, "Super class", objClass.getSuperclass().getName()); // print name of immediate superclass
	    	inspectInterfaces(objClass);

    }
    
    // notify user that inspecting superclass and print superclass name
    private void inspectSuperclass(Class objClass) {
    		System.out.println("\n-------- Superclass --------");
    		if (objClass.getSuperclass() != null) {
        		System.out.println("Inspecting superclass: " + objClass.getSuperclass().getSimpleName());
    		} else {
    			System.out.println("Superclass not found");
    		}
    }
    
    // print array name, type, length, and contents
    private void inspectArray(Object obj, Class objClass) {
    		System.out.println("-------- Array --------");
    		out.format(fmt, "Name", objClass.getSimpleName());
    		out.format(fmt, "Type", objClass.getComponentType());
    		out.format(fmt, "Length", Array.getLength(obj));

    		System.out.println("Contents:");
    		for (int i = 0; i < Array.getLength(obj); i++) {
    			System.out.println("[" + i + "]" + Array.get(obj, i));
    			
    			// check if array index is not empty
//    			if (Array.get(obj, i) != null)
//    				inspect(Array.get(obj, i), recursive);
    		}
    }
    
    public void inspectInterfaces(Class objClass) {
    		System.out.println("\n-------- Interfaces --------");
	    	Class[] interfaces = objClass.getInterfaces();
	    	// Print the name of the interfaces the class implements
	    	if (objClass.getInterfaces().length < 1) {
	    		System.out.println("No interfaces implemented");
	    	} else {
		    	for (Class i : interfaces) {
		    		out.format(fmt, "Interfaces implemented", i.getSimpleName());
		    	}
	    	}
	    	System.out.println("");
    }

    
    public void inspectMethods(Class objClass) {
    	System.out.println("\n-------- Methods --------");
    		System.out.println("Inspecting methods in class: " + objClass.getSimpleName());
		Method[] allMethods = objClass.getDeclaredMethods();
		
		for (Method m : allMethods) {
			int numberOfExceptionTypes = m.getExceptionTypes().length;
			System.out.println("");
			out.format(fmt, "Method name", m.getName()); 
			if (numberOfExceptionTypes < 1) {
				System.out.println(" No exceptions thrown");
			} else {
				for (Class exception : m.getExceptionTypes()) {
					out.format(fmt, "ExceptionThrown", exception.getName());
				}
			}
			if (m.getParameters().length < 1) {
				System.out.println(" No parameters taken");
			} else {
				for (Parameter parameter : m.getParameters()) {
					out.format(fmt, "ParameterType", parameter.getParameterizedType());
				}
			}
			out.format(fmt, "ReturnType", m.getReturnType().getName());
			out.format(fmt, "Modifiers", Modifier.toString(m.getModifiers()));
		}
    }
    		
    
    
    public void inspectConstructors(Class objClass) {
    		System.out.println("\n-------- Constructors --------");
		Constructor[] allConstructors = objClass.getDeclaredConstructors();
		for (Constructor con : allConstructors) {
			Class<?>[] paramTypes = con.getParameterTypes();
			out.format(fmt, "Constructor", con.getName());
				for (Parameter parameter : con.getParameters()) {
					out.format(fmt, "ParameterType", parameter.getParameterizedType());
				}
			out.format(fmt, "Modifiers", Modifier.toString(con.getModifiers()) + "\n");
		}

    }
    
    //-----------------------------------------------------------
    private void inspectFieldClasses(Object obj,Class objClass,
				     Vector objectsToInspect,boolean recursive)
    {
		if(objectsToInspect.size() > 0 )
		    System.out.println("\n---- Inspecting Field Classes ----");
		
		Enumeration e = objectsToInspect.elements();
		while(e.hasMoreElements())
		    {
			Field f = (Field) e.nextElement();
			System.out.println("Inspecting Field: " + objClass.getSimpleName() + "." + f.getName() + ":" + f.getType());
			
			try
			    {
				System.out.println("******************");
				inspect( f.get(obj) , recursive);
				System.out.println("******************");
			    }
			catch(Exception exp) { exp.printStackTrace(); }
		    }
    }
    

    //-----------------------------------------------------------
    public void inspectFields(Object obj,Class objClass,Vector objectsToInspect)
    {
    		System.out.println("\n-------- Inspecting Fields --------");
    		System.out.println("Inspecting fields in class: " + objClass.getSimpleName());
    		// ensure fields exist
    		if(objClass.getDeclaredFields().length >= 1)
    	    {
	    		for (Field f : objClass.getDeclaredFields()) {	
	    			if(! f.getType().isPrimitive() ) // if the field is not primitive, add to objectsToInspect vector
	    			    objectsToInspect.addElement( f );
	    			f.setAccessible(true);
	    			try
	    		    {
	    				System.out.println("");
		    			// check if the field is an array, then print out information about array
		    			if (f.getType().isArray()) {
		    				System.out.println("Field is an array\n");
			    			out.format(fmt, "Field name", f.getName());
			        		out.format(fmt, "Type", f.getType().getComponentType());
			        		out.format(fmt, "Length", Array.getLength(f.get(obj)));
			        		System.out.println("Contents:");
			        		for (int i = 0; i < Array.getLength(f.get(obj)); i++) {
			        			System.out.println("[" + i + "]" + Array.get(f.get(obj), i));
			        		}
		    			} else { // print out as normal
			    			out.format(fmt, "Field name", f.getName());
			    			out.format(fmt, "Value", f.get(obj));
			    			out.format(fmt, "Type", f.getType());
			    			out.format(fmt, "Modifiers", Modifier.toString(f.getModifiers()));
			    			out.format(fmt, "Declaring class", f.getDeclaringClass());

		    			}
	    		    }
		    		catch(Exception e) {
		    			e.printStackTrace();
		    		}    
			}
    	    } else {
    	    		System.out.println("Field does not exist");
    	    }
		if(objClass.getSuperclass() != null) { // if objClass has a super class, inspect its fields
			inspectSuperclass(objClass);
			inspectFields(obj, objClass.getSuperclass() , objectsToInspect);
		}
	}  
}
