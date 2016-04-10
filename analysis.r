t=read.csv("large2.csv")
summary(t)

library(ggplot2)
ggplot(t, aes(x=S, fill=Heuristic)) + geom_histogram()
