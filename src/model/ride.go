package model

import (
	logger "github.com/sirupsen/logrus"
)

type Ride struct {
	Id int
	BeginRow     int
	BeginColumn  int
	EndRow       int
	EndColumn    int
	EarlistStart int
	LatestFinish int
	Done         bool
}

func (r *Ride) LogContent() {
	logger.Printf("Ride{begin : [%d,%d], end : [%d,%d], earliestStart : %d, latestFinish : %d}",
		r.BeginRow, r.BeginColumn, r.EndRow, r.EndColumn, r.EarlistStart, r.LatestFinish)
}
