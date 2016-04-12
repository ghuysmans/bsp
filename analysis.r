#!/usr/bin/env Rscript
library(optparse)
options=list(make_option(c("-e", "--exclude"), help="exclude a heuristic"))
parser=OptionParser("usage: %prog [options] data", options)
args=parse_args(parser, positional_arguments=T)

filename=args$args[1]
t=read.csv(filename)
if (!is.null(args$options$exclude))
	t=t[t$Heuristic!=args$options$exclude,]

library(ggplot2)
ggplot(t, aes(x=S, color=Heuristic)) + geom_freqpoly() +
	labs(x="Total Segments", y="Count") +
	theme_bw() + theme(legend.position="top")
