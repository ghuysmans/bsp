t=read.csv(commandArgs(trailingOnly=T)[1])
summary(t)

library(ggplot2)
ggplot(t, aes(x=S, fill=Heuristic)) + geom_histogram()
