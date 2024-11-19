package io.wende.aoc.twentythree

import io.wende.aoc.common.Task

class Five(test: Boolean) : Task(test) {

    companion object {

        var singleTuples: List<Tuple> = mutableListOf()
        var seedRanges: List<Pair<Long, Long>> = mutableListOf()
        var humidityToLocation: List<GardenMap> = mutableListOf()
        var temperatureToHumidity: List<GardenMap> = mutableListOf()
        var lightToTemperature: List<GardenMap> = mutableListOf()
        var waterToLight: List<GardenMap> = mutableListOf()
        var fertilizerToWater: List<GardenMap> = mutableListOf()
        var soilToFertilizer: List<GardenMap> = mutableListOf()
        var seedToSoil: List<GardenMap> = mutableListOf()
    }

    override fun run() {
        this.prepareInput()
        println("Lowest seed location is ${singleTuples.sortedBy { it.location }.first().location}")
        println("... lean back, this might take a while...")
        println("\nLowest seed range location is ${seedRanges.minOf { findMinimumLocation(it) }}")
    }

    fun findMinimumLocation(range: Pair<Long, Long>): Long {
        var minLocation = Long.MAX_VALUE

        for(i in range.first .. range.first + range.second) {
            minLocation = minOf(minLocation, this.createTuple(i, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight,
                lightToTemperature, temperatureToHumidity, humidityToLocation).location)
        }
        return minLocation
    }

    private fun prepareInput() {
        val mutableInput: MutableList<String> = this.input.filter { it.isNotBlank() }.toMutableList()

        var subList = mutableInput.subList(mutableInput.lastIndexOf("humidity-to-location map:"), mutableInput.size)
        humidityToLocation = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("temperature-to-humidity map:"), mutableInput.size)
        temperatureToHumidity = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("light-to-temperature map:"), mutableInput.size)
        lightToTemperature = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("water-to-light map:"), mutableInput.size)
        waterToLight = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("fertilizer-to-water map:"), mutableInput.size)
        fertilizerToWater = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("soil-to-fertilizer map:"), mutableInput.size)
        soilToFertilizer = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        subList = mutableInput.subList(mutableInput.lastIndexOf("seed-to-soil map:"), mutableInput.size)
        seedToSoil = this.generateGardenMaps(subList.subList(1, subList.size))
        mutableInput.removeAll(subList)

        singleTuples = this.createTuples(mutableInput.get(0).split(": ")
            .get(1).split(" ").map {
                it.toLong()
            }, seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation)

        val seedRangeInput = mutableInput.get(0).split(": ").get(1).split(" ").map { it.toLong() }
        for(i in seedRangeInput.indices step 2) {
            seedRanges.addLast(Pair(seedRangeInput[i], seedRangeInput.get(i + 1) - 1))
        }
    }

    private fun createTuples(seeds: List<Long>, seedToSoil: List<GardenMap>, soilToFertilizer: List<GardenMap>,
                             fertilizerToWater: List<GardenMap>, waterToLight: List<GardenMap>, lightToTemperature: List<GardenMap>,
                             temperatureToHumidity: List<GardenMap>, humidityToLocation: List<GardenMap>): List<Tuple>  = seeds.map {
            this.createTuple(it, seedToSoil, soilToFertilizer, fertilizerToWater,
                waterToLight, lightToTemperature, temperatureToHumidity, humidityToLocation)
        }


    private fun createTuple(seed: Long, seedToSoil: List<GardenMap>, soilToFertilizer: List<GardenMap>,
                             fertilizerToWater: List<GardenMap>, waterToLight: List<GardenMap>, lightToTemperature: List<GardenMap>,
                             temperatureToHumidity: List<GardenMap>, humidityToLocation: List<GardenMap>): Tuple {
            val soil = seedToSoil.find { it.isInRange(seed) }?.getRangeDestination(seed) ?: seed
            val fertilizer = soilToFertilizer.find { it.isInRange(soil) }?.getRangeDestination(soil) ?: soil
            val water = fertilizerToWater.find { it.isInRange(fertilizer) }?.getRangeDestination(fertilizer) ?: fertilizer
            val light = waterToLight.find { it.isInRange(water) }?.getRangeDestination(water) ?: water
            val temperature = lightToTemperature.find { it.isInRange(light) }?.getRangeDestination(light) ?: light
            val humidity = temperatureToHumidity.find { it.isInRange(temperature) }?.getRangeDestination(temperature) ?: temperature
            val location = humidityToLocation.find { it.isInRange(humidity) }?.getRangeDestination(humidity) ?: humidity
            return Tuple(seed, soil, fertilizer, water, light, temperature, humidity, location)
    }

    private fun generateGardenMaps(lines: List<String>): List<GardenMap> {
        return lines.map {
            val values = it.split(" ").map { it.toLong() }
            GardenMap(values[0], values[1], values[2])
        }
    }

    data class GardenMap(var destination: Long, var source: Long, var range: Long) {
        fun isInRange(source: Long): Boolean = source in this.source.. this.source + this.range
        fun getRangeDestination(source: Long): Long = if(this.isInRange(source)) source - this.source + this.destination else source
    }

    data class Tuple(var seed: Long, var soil: Long, var fertilizer: Long, var water: Long, var light: Long, var temperature: Long, var humidity: Long, var location: Long) {
        fun equalSeedToLoaction(): Boolean = this.seed == this.location
    }
}