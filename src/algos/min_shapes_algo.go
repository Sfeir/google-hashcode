package algos

import "github.com/Sfeir/google-hashcode-lille/src/model"

type shape struct {
	row, column int
}

func calculateAvailableShapes(size int) []shape {
	// TODO(sylvain) return [](row,columns)
	return []shape{}
}

func shapeCanFit(s shape, c [][]model.Cell, x int, y int) bool {
	// TODO(sylvain) return true if all the cells between (x,y) and that fits in S are available
	return false
}

func declareAllCellsTaken(s shape, c [][]model.Cell, x int, y int) model.Slice {
	// TODO(sylvain) set all the taken to true for cell that are in s starting from (x,y)
	return model.Slice{}
}

func MinShapesAlgo(inputs *model.Inputs) []model.Slice {
	availableShapes := calculateAvailableShapes(inputs.MaxSizeSlice)
	result := []model.Slice{}
	for x := 0; x < inputs.NbRows; x++ {
		for y := 0; y < inputs.NbColumns; y++ {
			for _, s := range availableShapes {
				if (shapeCanFit(s, inputs.Cells, x, y)) {
					result = append(result, declareAllCellsTaken(s, inputs.Cells, x, y))
				}
			}
		}
	}
	return result
}
