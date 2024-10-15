export interface StepCounterPlugin {

  start():Promise<void>
  getStepCount():Promise<{steps: number}>
  stop():Promise<void>
  
}
