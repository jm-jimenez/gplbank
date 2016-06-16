var jade = require("gulp-jade"),
	gulp = require("gulp"),
	less = require("gulp-less"),
	cssmin = require("gulp-cssmin"),
	rename = require("gulp-rename"),
	uglify = require("gulp-uglify"),
	connect = require("gulp-connect"),
	concat = require("gulp-concat");

gulp.task("connect", function(){
	connect.server({
    root: 'dist',
    livereload: true
  });
});

gulp.task("jade", function(){
	gulp.src("./templates/*.jade")
	.pipe(jade())
	.pipe(gulp.dest("./dist"));
});

gulp.task("less", function(){
	gulp.src("./less/*.less")
	.pipe(less())
	.pipe(concat("app.css"))
	.pipe(cssmin())
	.pipe(rename({suffix : ".min"}))
	.pipe(gulp.dest("./dist/css"));
});

gulp.task("js", function(){
	gulp.src("./js/*.js")
	.pipe(concat("app.js"))
	.pipe(uglify())
	.pipe(gulp.dest("./dist/js"));
});

gulp.task("default", ["connect","jade", "less","js"], function(){
	var watchjade = gulp.watch("./templates/*.jade", ["jade"]);
	var watchless = gulp.watch("./less/*.less", ["less"]);
	var watchjs = gulp.watch("./js/*.js", ["js"]);
});