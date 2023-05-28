module BADProject {
	opens main;
	exports data;
	requires transitive java.sql;
	requires javafx.graphics;
	requires javafx.controls;
	requires javafx.base;
	requires java.base;
}