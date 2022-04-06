"# mtcg_mimimiii" 

"Monster Trading Cards Game (MTCG) __ This HTTP/REST-based server 
is built to be a platform for trading and battling with and 
against each other in a magical card-game world."_seeIndication




-> design/ lessons learned

The class "Procedure" in the package "application" handles the connection to
the frontend. Those class decides based on input and method of the http-header
which action regarding the database is carried out. The maven dependency: "fasterxml.jackson.core.databind"
carries out Serialization and Deserialization. As Jackson was recommended; I wanted to use it.
But I had a few problems with this, because I researched and found answers, that I have to integrate 
it in my "POM.xml - file". I thought easy, no problem to find an actual dependency link and to do that.
But then those implements never worked out, there was just compiler-bugs and the class was not found by
the maven. also not when connected with the small maven sign. After handing in the project the secound 
time I gave up and started a new project without (ProjectObjectModel) POM.xml-file. 
Integrating the dependencies by clicking on(in JavaIntelliJ_IDE):
-file
-project-structure
-project settings
-libraries
and from that moment an the link was no more red marked. Finally it worked and run without ignoring 
my "objectmapper" for instance. 

We got an Frequence-diagram drawn by our lector, but first section stood CURL.
I did not knew before that CURL stands for "Client Uniform Resource Locator". And some more
questions in my head regarding this exercise may was solved by just asking Google. 
Object Oriented Programming was not the challenge, it was starting JAVA and having any clue about 
those API - servico. 


-> unit test design
JAVA Junit 
maven dependency:  junit.jupiter.engine


"JUnit is a unit testing framework for the Java programming language. 
JUnit has been important in the development of test-driven development, 
and is one of a family of unit testing frameworks which is collectively 
known as xUnit that originated with SUnit." _source https://en.wikipedia.org/wiki/JUnit


-> time spent
Phu...emmm; time spent: that is hard for me to answer, because firstly I needed to google what is an HTTP/Rest-based server. Splitted in each word and how the HTTP-protocol works exactly.
Afterwards I started in Christmas holidays with doing an "Udemy-Course" in Java programming. So that was still not enough, I already had the JAVA-Book from Michael Kofler.
Then I tried the exercise to begin with defining classes. With breaks each day around 4 - 6 hours.
Sometimes my thoughts drifted away and I searched in the moodle course "SoftwareEngineering1" some stuff in between or saw the internet empty. Honestly if I get paid by 
hours and not quality, may I could have made 5* vacation in Hawaii - however this is the result I hand in.


-> link to git

https://github.com/Johanna0815/mtcg_mimimiii.git

