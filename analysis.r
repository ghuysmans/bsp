#!/usr/bin/env Rscript
library(optparse)
options=list(make_option(c("-e", "--exclude"), help="exclude a heuristic"))
parser=OptionParser("usage: %prog [options] data", options)
args=parse_args(parser, positional_arguments=T)

filename=args$args[1]
t=read.csv(filename)
if (!is.null(args$options$exclude))
	t=t[t$Heuristic!=args$options$exclude,]
summary(t)

library(ggplot2)
ggplot(t, aes(x=S, fill=Heuristic)) + geom_histogram() +
	labs(x="Total Segments", y="Count") #+ labs(title=filename)
