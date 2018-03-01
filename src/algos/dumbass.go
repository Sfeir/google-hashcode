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
					if !ride.Done {
						prendLaCourse := TrajetPossibleJusque(step, taxi, ride)
						if prendLaCourse >= 0 && taxi.Busy <= 0 {
							heurePriseCourse := Distance(taxi.ColumnPos, taxi.RowPos, ride.BeginColumn, ride.BeginRow)
							taxi.Busy = heurePriseCourse + Distance(ride.BeginColumn, ride.BeginRow, ride.EndColumn, ride.EndRow)
							ride.Done = true
							logger.Info("Add ride ", idRide, " to taxi ", idTaxi)
							courses = AddRideToVehicule(courses, idRide, idTaxi)
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
