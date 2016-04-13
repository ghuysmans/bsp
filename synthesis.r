#!/usr/bin/env Rscript
library(tools)
args=commandArgs(trailingOnly=T)
dir=args[1]
type=strsplit(args[2], "-")[[1]][1]
pattern=paste(dir, type, "*.csv", sep="")
tests=Sys.glob(pattern)
n=3*length(tests) #FIXME hardcoded heuristic count
out=data.frame(Heuristic=n, I=numeric(n), m=numeric(n))
i=1
for (f in tests) {
	scene=paste(file_path_sans_ext(f), ".txt", sep="")
	segs=as.integer(strsplit(readLines(scene, n=1), " ")[[1]][4])
	stats=read.csv(f)
	for (h in unique(stats$Heuristic)) {
		out$Heuristic[i]=h
		out$I[i]=segs
		this=stats[stats$Heuristic==h,]$S
		out$mean[i]=mean(this)
		i=i+1
	}
}

#TODO plotting the whole dataset and using geom_smooth would've been great...
library(ggplot2)
ggplot(out, aes(x=I, color=Heuristic, y=mean)) +
	geom_point() + geom_abline(slope=1) +
	labs(x="Input", y="Output") +
	theme_bw() + theme(legend.position="top")
