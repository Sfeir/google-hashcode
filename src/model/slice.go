package model

import (
	"strconv"
)

type Slice struct {
	StartX, StartY, EndX, EndY int
}

func (s Slice) ToStr() string {
	result := ""
	result += strconv.Itoa(s.StartX) + " "
	result += strconv.Itoa(s.StartY) + " "
	result += strconv.Itoa(s.EndX) + " "
	result += strconv.Itoa(s.EndY)
	return result
}
