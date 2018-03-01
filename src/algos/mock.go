package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
)

func MockAlgo() []model.Course {
	for i := 0; i < model.NbRides; i++ {
		//	model.Rides[i].LogContent()
	}

	mockCourseOne := model.Course{Vehicule: 1, Rides: []int{0}}
	mockCourseTwo := model.Course{Vehicule: 2, Rides: []int{2, 1}}
	return []model.Course{mockCourseOne, mockCourseTwo}
}
