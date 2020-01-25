package riscv

import chisel3._

class ID_Pipeline extends Module{
	val io = IO(new Bundle{
		

		//INPUTS
		val rd_Input = Input(UInt(5.W))
		val strData_Input = Input(SInt(32.W))
		val OperandA_Input = Input(SInt(32.W))
		val OperandB_Input = Input(SInt(32.W))
		val aluCtrl_Input = Input(UInt(5.W))
		val RegWrite_Input = Input(UInt(1.W))
		val MemWrite_Input = Input(UInt(1.W))
		val MemtoReg_Input = Input(UInt(1.W))
		val MemRead_Input = Input(UInt(1.W))
		

		
		


		//OUTPUTS
		val rd_Output = Output(UInt(5.W))
		val strData_Output = Output(SInt(32.W))
		val OperandA_Output = Output(SInt(32.W))
		val OperandB_Output = Output(SInt(32.W))
		val aluCtrl_Output = Output(UInt(5.W))
		val RegWrite_Output = Output(UInt(1.W))
		val MemWrite_Output = Output(UInt(1.W))
		val MemtoReg_Output = Output(UInt(1.W))
		val MemRead_Output = Output(UInt(1.W))
		
		
		
		
		
		
		
		

})


		val reg_MemWrite = RegInit(0.U(1.W))
		val reg_MemRead = RegInit(0.U(1.W))
		val reg_MemtoReg = RegInit(0.U(1.W))
		val reg_OperandA = RegInit(0.S(32.W))
		val reg_operandB = RegInit(0.S(32.W))
		val reg_rd = RegInit(0.U(5.W))
		val reg_strData = RegInit(0.S(32.W))
		val reg_aluCtrl = RegInit(0.U(5.W))
		val reg_RegWrite = RegInit(0.U(1.W))


		reg_MemWrite := io.MemWrite_Input
		reg_MemRead := io.MemRead_Input
		reg_MemtoReg := io.MemtoReg_Input
		reg_OperandA := io.OperandA_Input
		reg_OperandB := io.OperandB_Input
		reg_rd := io.rd_Input
		reg_strData := io.strData_Input
		reg_aluCtrl := io.aluCtrl_Input
		reg_RegWrite := io.RegWrite_Input



		io.MemWrite_Output := reg_MemWrite
		io.MemRead_Output := reg_MemRead
		io.MemtoReg_Output := reg_MemtoReg
		io.OperandA_Output := reg_OperandA
		io.OperandB_Output := reg_OperandB
		io.rd_Output := reg_rd
		io.strData_Output := reg_strData
		io.aluCtrl_Output := reg_aluCtrl
		io.RegWrite_Output := reg_RegWrite

}
