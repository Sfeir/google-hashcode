package algos

import "github.com/Sfeir/google-hashcode-lille/src/model"

type shape struct {
	row, column int
}

func calculateAvailableShapes(size int) []shape {
	var result = []shape{}
	result = append(result, shape{row: 1, column: size})
	result = append(result, shape{row: size, column: 1})
	// TODO(sylvain) finish the rectangles and square shapes.
	return result
}

func shapeCanFit(s shape, c [][]model.Cell, x int, y int) bool {
	if (x+s.column > len(c[0])) || (y+s.row > len(c)) {
		return false
	}

	for i := x; i < x+s.row; i++ {
		for j := y; j < y+s.column; j++ {
			if !c[i][j].Taken {
				return false
			}
		}
	}
	return true
}

func declareAllCellsTaken(s shape, c [][]model.Cell, x int, y int) model.Slice {
	for i := x; i < x+s.row; i++ {
		for j := y; j < y+s.column; j++ {
			c[i][j].Taken = true
		}
	}
	return model.Slice{StartX: x, StartY: y, EndX: x + s.row, EndY: y + s.column}
}

func MinShapesAlgo(inputs *model.Inputs) []model.Slice {
	availableShapes := calculateAvailableShapes(inputs.MaxSizeSlice)
	result := []model.Slice{}
	for x := 0; x < inputs.NbRows; x++ {
		for y := 0; y < inputs.NbColumns; y++ {
			for _, s := range availableShapes {
				if shapeCanFit(s, inputs.Cells, x, y) {
					result = append(result, declareAllCellsTaken(s, inputs.Cells, x, y))
				}
			}
		}
	}
	return result
}
