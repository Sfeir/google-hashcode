package model

type Ride struct {
	BeginRow    int
	BeginColumn int
	EndRow      int
	EndColumn   int
	EarlistStart int
	LatestFinish int
}
