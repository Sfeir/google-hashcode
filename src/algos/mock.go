package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
)

func MockAlgo() []model.Course {
	for i:=0; i < model.NbRides; i++ {
		model.Rides[i].LogContent()
	}
	return []model.Course{}
}
