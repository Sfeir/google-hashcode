package algos

import (
	"github.com/Sfeir/google-hashcode-lille/src/model"
	logger "github.com/sirupsen/logrus"
)

func Dumbass() []model.Course {

	courses := make([]model.Course, model.FleetSize)

	for step := 0; step < model.TotalTime; step++ {
		for idTaxi, taxi := range model.Fleet {
			if taxi.Busy <= 0 {
				for idRide, ride := range model.Rides {
					if !ride.Done && taxi.Busy <= 0 {
						prendLaCourse := TrajetPossibleJusque(step, taxi, ride)
						if prendLaCourse >= 0 {
							heurePriseCourse := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
							taxi.Busy = heurePriseCourse + Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
							ride.Done = true
							taxi.ColumnPos = ride.EndColumn
							taxi.RowPos = ride.EndRow
							logger.Info("Add ride ", idRide, " to taxi ", idTaxi, " will finish in ", taxi.Busy, "steps")
							courses = AddRideToVehicule(courses, idRide, idTaxi)
							break
						}
					}
				}
			} else {
				taxi.Busy--
			}
		}
	}

	return courses
}
