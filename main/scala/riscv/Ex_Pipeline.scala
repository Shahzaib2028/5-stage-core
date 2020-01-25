package riscv

import chisel3._

class Ex_Pipeline extends Module{
	val io = IO(new Bundle{


		//INPUTS
		val EX_MemWrite_Input = Input(UInt(1.W))
        	val EX_MemRead_Input = Input(UInt(1.W))
        	val EX_RegWrite_Input = Input(UInt(1.W))
        	val EX_MemToReg_Input = Input(UInt(1.W))
        	val EX_strData_Input = Input(SInt(32.W))
        	val EX_rd_Input = Input(UInt(5.W))
        	val Ex_ALU_output_Input = Input(SInt(32.W))
	val EX_alu_Output_input = Input(SInt(32.W))
	val Ex_Alu_branch_output_Input = Input(UInt(1.W))


		
		//OUTPUTS
		val Ex_MemWrite_Output = Output(UInt(1.W))
        	val Ex_MemRead_Output = Output(UInt(1.W))
        	val Ex_RegWrite_Output = Output(UInt(1.W))
        	val Ex_MemToReg_Output = Output(UInt(1.W))
        	val Ex_strData_Output = Output(SInt(32.W))
        	val Ex_rd_Output = Output(UInt(5.W))
        	val Ex_ALU_Output_Output = Output(SInt(32.W))
	val Ex_alu_Output_output = Output(SInt(32.W))
	val Ex_Alu_branch_Output_Output = Output(UInt(1.W))

})
		val reg_EX_MemWrite = RegInit(0.U(1.W))
		val reg_EX_MemRead = RegInit(0.U(1.W))
		val reg_EX_MemToReg = RegInit(0.U(1.W))
		val reg_EX_rd = RegInit(0.U(5.W))
		val reg_EX_strData = RegInit(0.S(32.W))
		val reg_EX_ALU_output = RegInit(0.S(32.W))
		val reg_RegWrite = RegInit(0.U(1.W))
  	val Ex_alu_Output_output = RegInit(0.S(32.W))
  	val Ex_Alu_branch_output = RegInit(0.U(1.W))



		reg_EX_MemWrite := io.EX_MemWrite_Input
		reg_EX_MemRead := io.EX_MemRead_Input
		reg_EX_MemToReg := io.EX_MemToReg_Input
		reg_EX_rd := io.EX_rd_Input
		reg_EX_strData := io.EX_strData_Input
		reg_EX_ALU_output := io.Ex_ALU_output_Input
		reg_RegWrite := io.EX_RegWrite_Input
  	Ex_alu_Output_output := io.EX_alu_Output_input
  	Ex_Alu_branch_output := io.Ex_Alu_branch_output_Input


		io.Ex_MemWrite_Output := reg_EX_MemWrite
		io.Ex_MemRead_Output := reg_EX_MemRead
		io.Ex_MemToReg_Output := reg_EX_MemToReg
		io.Ex_rd_Output := reg_EX_rd
		io.Ex_strData_Output := reg_EX_strData
		io.Ex_ALU_Output_Output := reg_EX_ALU_output
		io.Ex_RegWrite_Output := reg_RegWrite
  	io.Ex_alu_Output_output := Ex_alu_Output_output
  	io.Ex_Alu_branch_Output_Output := Ex_Alu_branch_output
		

}
