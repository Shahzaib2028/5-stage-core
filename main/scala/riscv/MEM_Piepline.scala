package riscv

import chisel3._

class MEM_Pipeline extends Module{
	val io = IO(new Bundle{


		//INPUTS
		val Data_RegWrite_Input = Input(UInt(1.W))
        	val Data_MemToReg_Input = Input(UInt(1.W))
        	val Data_rd_Input = Input(UInt(5.W))
        	val Data_dataOutput_Input = Input(SInt(32.W))
        	val Data_ALU_output_Input = Input(SInt(32.W))


		//OUTPUT
		val Data_RegWrite_Output = Output(UInt(1.W))
        	val Data_MemToReg_Output = Output(UInt(1.W))
        	val Data_rd_Output = Output(UInt(5.W))
        	val Data_dataOutput_Output = Output(SInt(32.W))
        	val Data_ALU_output_Output = Output(SInt(32.W))

})
		val reg_Data_MemToReg = RegInit(0.U(1.W))
		val reg_Data_rd = RegInit(0.U(5.W))
		val reg_Data_dataOutput = RegInit(0.S(32.W))
		val reg_Data_ALU_output = RegInit(0.S(32.W))
		val reg_Data_RegWrite = RegInit(0.U(1.W))


		reg_Data_MemToReg := io.Data_MemToReg_Input
		reg_Data_rd := io.Data_rd_Input
		reg_Data_dataOutput := io.Data_dataOutput_Input
		reg_Data_ALU_output := io.Data_ALU_output_Input
		reg_Data_RegWrite := io.Data_RegWrite_Input


		io.Data_MemToReg_Output := reg_Data_MemToReg
		io.Data_rd_Output := reg_Data_rd
		io.Data_dataOutput_Output := reg_Data_dataOutput
		io.Data_ALU_output_Output := reg_Data_ALU_output
		io.Data_RegWrite_Output := reg_Data_RegWrite

}
