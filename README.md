The judith programming language
===============================

[![Build Status](https://travis-ci.org/markspanbroek/judith.svg)](https://travis-ci.org/markspanbroek/judith)

Introduction
------------

The obligatory Hello World example:

	object output := Objects.IO.StandardOutput
	
	output.write("Hello World!")
	
Variables
---------

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

Objects
-------

Anything that you can assign to a variable is an object. 
This means that the number _3_ is an object, the string of text _"hello"_ is an object, and objects from the object library are all objects.

Conditional execution
---------------------

Conditional execution is supported by _if_ statements. Multiple conditions can be specified, and are evaluated in order.

	object output := Objects.IO.StandardOutput
	
	object answer := 43
	
	if answer < 42
		output.write("too low")
	|| answer = 42
		output.write("correct")
	|| answer > 42
		output.write("too high")
	fi
		
Repetition
----------

Repeated execution is supported by _do_ statements. Like the _if_ statement, multiple conditions can be specified. The _do_ statement will repeat as long as one of its conditions evaluates to true.

	object output := Objects.IO.StandardOutput
	
	object answer := 40
	
	do answer < 42
		answer := answer + 1
	|| answer > 42
		answer := answer - 1
	od
	
	output.write("the answer is: " + answer)


Replace
-------

The replace method is present on all objects, and can be used to replace an object instance with a different object instance. The only restriction is that the replacement should be a subtype of the object that is being replaced.

The replace method is especially suited for adding functionality to objects from the object library. The example below adds a factorial method to all numbers.

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
