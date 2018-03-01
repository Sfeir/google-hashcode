package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
)

func Dumbass() []model.Course {

	courses := []model.Course{}

	for step := 0; step < model.TotalTime; step++ {
		for idTaxis, taxi := range model.Fleet {
			for idRide, ride := range model.Rides {
				prendLaCourse := TrajetPossible(step, taxi, ride)
				if prendLaCourse && taxi.Busy <= 0 {
					heurePriseCourse := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
					taxi.Busy = heurePriseCourse + Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
				}
			}
		}
	}

	return courses
}
