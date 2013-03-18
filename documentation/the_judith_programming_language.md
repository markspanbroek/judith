The judith programming language
===============================

Introduction
------------

The obligatory Hello World example:

	object output := Objects.IO.StandardOutput
	
	output.write("Hello World!")
	
Basics
------

### Variables ###

When you first introduce a variable, you have to explicitly declare it using the
_object_ keyword. For example:

	object amount := 10
	
This declares a variable named _amount_, with an initial value of 10. Specifying an
initial value is not optional. Every variable that you declare must have an initial
value assigned to it.

After declaration any value may be assigned to the variable:

	amount := 4
	amount := "a lot"

As you have probably figured out by now, variables do not have a type. You are free
to first assign a number to the _amount_ variable, and later on assign a string of text.

### Objects ###

Anything that you can assign to a variable is an object. 
This means that the number `3`
is an object, the string of text `"hello"` is an object and objects from the object library


Numbers

Text

Are all objects

### Methods ###

### Object library ###

### Conditional execution ###

	object output := Objects.IO.StandardOutput
	
	object answer := 43
	
	if answer < 42
		output.write("too low")
	|| answer = 42
		output.write("correct")
	|| answer > 42
		output.write("too high")
	fi
		
### Repetition ###

	object output := Objects.IO.StandardOutput
	
	object answer := 40
	
	do answer < 42
		answer := answer + 1
	|| answer > 42
		answer := answer - 1
	od
	
	output.write("the answer is: " + answer)


Object library
--------------

### Text ###

### Numbers ###

### IO ###


Inheritance
-----------

### Parent ###

Runtime refactoring
-------------------

### Replace ###

	object output := Objects.IO.StandardOutput

	Number.replace(Number
	|[
		method factorial
		[
			if (self = 0)
				result := 1
			else
				result := self * (self-1).factorial
			fi
		]
	]|)

	output.write("42! = " + 42.factorial)



### Rename ###


Testing
-------

### Assert ###